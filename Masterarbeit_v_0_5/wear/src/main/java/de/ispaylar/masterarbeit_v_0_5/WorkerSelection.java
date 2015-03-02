package de.ispaylar.masterarbeit_v_0_5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class WorkerSelection extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_selection_grid);
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new WorkerFragment(this, getFragmentManager()));


//        pager.setOnClickListener(new OnClickListener() {
//            public void onClick(View position) {
//                switch (position)
//                {
//                    case 0:Intent teschemacher = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(teschemacher);
//                        break;
//
//                    case 1:
//                        Intent steinhaeusser = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(steinhaeusser);
//                        break;
//
//                    case 2:
//                        Intent meis = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(meis);
//                        break;
//                    case 3:
//                        Intent lock = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(lock);
//
//                    case 4:
//                        Intent plehn = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(plehn);
//                        break;
//
//                    case 5:
//                        Intent dollinger = (new Intent(WorkerSelection.this, MainActivity.class));
//                        startActivity(dollinger);
//                        break;
//
//
//                }
//
//            }
//        });

        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);
    }





    class WorkerFragment extends FragmentGridPagerAdapter {

        private static final int TRANSITION_DURATION_MILLIS = 100;

        private final Context mContext;
        private List<Row> mRows;
        private ColorDrawable mDefaultBg;



        private ColorDrawable mClearBg;
        //---------------------------------------------------------------------------------------------------------------------
//  Here you NAME the certain workers
        public WorkerFragment(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;

            mRows = new ArrayList<Row>();

            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.teschemacher)));
            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.steinhaeusser)));
            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.meis)));
            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.lock)));
            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.plehn)));
            mRows.add(new Row(cardFragment(R.string.workerselection_title, R.string.dollinger)));

//      In case in one row several cardFragments are needed
//        mRows.add(new Row(
//                cardFragment(R.string.cards_title, R.string.cards_text),
//                cardFragment(R.string.expansion_title, R.string.expansion_text)));

            mDefaultBg = new ColorDrawable(R.color.dark_grey);
            mClearBg = new ColorDrawable(android.R.color.transparent);
        }
//---------------------------------------------------------------------------------------------------------------------

        //  no idea what is this all about... need for doublecheck
        LruCache<Integer, Drawable> mRowBackgrounds = new LruCache<Integer, Drawable>(3) {
            @Override
            protected Drawable create(final Integer row) {
                int resid = BG_IMAGES[row % BG_IMAGES.length];
                new DrawableLoadingTask(mContext) {
                    @Override
                    protected void onPostExecute(Drawable result) {
                        TransitionDrawable background = new TransitionDrawable(new Drawable[] {
                                mDefaultBg,
                                result
                        });
                        mRowBackgrounds.put(row, background);
                        notifyRowBackgroundChanged(row);
                        background.startTransition(TRANSITION_DURATION_MILLIS);
                    }
                }.execute(resid);
                return mDefaultBg;
            }
        };


//    for a certain background
//    LruCache<Point, Drawable> mPageBackgrounds = new LruCache<Point, Drawable>(3) {
//        @Override
//        protected Drawable create(final Point page) {
//            // place bugdroid as the background at row 2, column 1
//            if (page.y == 2 && page.x == 1) {
//                int resid = R.drawable.bugdroid_large;
//                new DrawableLoadingTask(mContext) {
//                    @Override
//                    protected void onPostExecute(Drawable result) {
//                        TransitionDrawable background = new TransitionDrawable(new Drawable[] {
//                                mClearBg,
//                                result
//                        });
//                        mPageBackgrounds.put(page, background);
//                        notifyPageBackgroundChanged(page.y, page.x);
//                        background.startTransition(TRANSITION_DURATION_MILLIS);
//                    }
//                }.execute(resid);
//            }
//            return GridPagerAdapter.BACKGROUND_NONE;
//        }
//    };



        public  MyCardFragment create(CharSequence title, CharSequence text, int iconRes) {
            MyCardFragment fragment = new MyCardFragment();
            Bundle args = new Bundle();
            if (title != null) args.putCharSequence("CardFragment_title", title);
            if (text != null) args.putCharSequence("CardFragment_text", text);
            if (iconRes != 0) args.putInt("CardFragment_icon", iconRes);
            fragment.setArguments(args);
            return fragment;
        }


        class MyCardFragment extends CardFragment {

            private View fragmentView;
            private View.OnClickListener listener;


            @Override
            protected View onCreateContentView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
                fragmentView = super.onCreateContentView(inflater, container, savedInstanceState);

                fragmentView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View view) {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    }
                });
                return fragmentView;
            }
            public void setOnClickListener(final View.OnClickListener listener) {
                this.listener = listener;
            }

        }




        private Fragment cardFragment(int titleRes, int textRes) {
            Resources res = mContext.getResources();
            final MyCardFragment fragment = (MyCardFragment) MyCardFragment.create(res.getText(titleRes), res.getText(textRes));
            fragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Intent i = new Intent(WorkerSelection.this, MainActivity.class);
//                i.putExtra("OBJECT_NAME", someObject);

                    startActivity(i);
                }
            });
            return fragment;

        }



//-------- altes Fragment-----------

//        private Fragment cardFragment(int titleRes, int textRes) {
//            Resources res = mContext.getResources();
//            CardFragment fragment =
//                    CardFragment.create(res.getText(titleRes), res.getText(textRes));
//            // Add some extra bottom margin to leave room for the page indicator
//            fragment.setCardMarginBottom(
//                    res.getDimensionPixelSize(R.dimen.card_margin_bottom));
//            return fragment;
//        }

//-------- altes Fragment-----------





        //---------------------------------------------------------------------------------------------------------------------
//  Here you are adding the background picture to the certain worker
        final int[] BG_IMAGES = new int[] {
                R.drawable.teschemacher,
                R.drawable.steinhaeusser,
                R.drawable.meis,
                R.drawable.lock,
                R.drawable.plehn,
                R.drawable.dollinger
        };
//---------------------------------------------------------------------------------------------------------------------

        /** A convenient container for a row of fragments. */
        private class Row {
            final List<Fragment> columns = new ArrayList<Fragment>();

            public Row(Fragment... fragments) {
                for (Fragment f : fragments) {
                    add(f);
                }
            }

            public void add(Fragment f) {
                columns.add(f);
            }

            Fragment getColumn(int i) {
                return columns.get(i);
            }

            public int getColumnCount() {
                return columns.size();
            }
        }

        @Override
        public Fragment getFragment(int row, int col) {
            Row adapterRow = mRows.get(row);
            return adapterRow.getColumn(col);
        }

        @Override
        public Drawable getBackgroundForRow(final int row) {
            return mRowBackgrounds.get(row);
        }

//    is needed in case of the certain background; see above
//    @Override
//    public Drawable getBackgroundForPage(final int row, final int column) {
//        return mPageBackgrounds.get(new Point(column, row));
//    }

        @Override
        public int getRowCount() {
            return mRows.size();
        }

        @Override
        public int getColumnCount(int rowNum) {
            return mRows.get(rowNum).getColumnCount();
        }

        class DrawableLoadingTask extends AsyncTask<Integer, Void, Drawable> {
            private static final String TAG = "Loader";
            private Context context;

            DrawableLoadingTask(Context context) {
                this.context = context;
            }

            @Override
            protected Drawable doInBackground(Integer... params) {
                Log.d(TAG, "Loading asset 0x" + Integer.toHexString(params[0]));
                return context.getResources().getDrawable(params[0]);
            }
        }
    }
}


//      ----Für den Fall, dass es eines Tages eine runde Uhr gibt....
//        final Resources res = getResources();
//        pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
//            @Override
//            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
//                // Adjust page margins:
//                //   A little extra horizontal spacing between pages looks a bit
//                //   less crowded on a round display.
//                final boolean round = insets.isRound();
//                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
//                int colMargin = res.getDimensionPixelOffset(round ?
//                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
//                pager.setPageMargins(rowMargin, colMargin);
//
//                // GridViewPager relies on insets to properly handle
//                // layout for round displays. They must be explicitly
//                // applied since this listener has taken them over.
//                pager.onApplyWindowInsets(insets);
//                return insets;
//            }
//
//
//        });


