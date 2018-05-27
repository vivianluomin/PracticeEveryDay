package vivian.com.testrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by asus1 on 2017/7/31.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable divider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    private int getSpanCount(RecyclerView parent){
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager)layoutManager).getSpanCount();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager)layoutManager).getSpanCount();

        }

        return spanCount;
   }

    public void drawHorizontal(Canvas c,RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i = 0;i<childCount;i++){
            final View child  = parent.getChildAt(i);
            final  RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams)child.getLayoutParams();
            final int left = child.getLeft()-params.leftMargin;
            final  int right = child.getRight()+params.rightMargin+divider.getIntrinsicWidth();
           // Log.d("width",String.valueOf(divider.getIntrinsicWidth()));
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);

        }
    }

    public void drawVertical(Canvas c,RecyclerView parent){
        final int childCount = parent.getChildCount();
        for(int i =0 ;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params =  (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop()-params.topMargin;
            int bottom = child.getBottom() +params.bottomMargin;
            int left = child.getRight()+params.rightMargin;
            int right = left+divider.getIntrinsicWidth();
            divider.setBounds(left,top,right,bottom);
            Log.d("wnw",String.valueOf(top)+"  "+String.valueOf(bottom)+"  "+String.valueOf(left)+
                    "   "+String.valueOf(right) );
            divider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL)
            {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent,int pos,int spanCount,
                              int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof  GridLayoutManager){
            childCount = childCount-childCount%spanCount;
            if(pos>=childCount)
                return true;
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager)layoutManager)
                    .getOrientation();
            if(orientation == StaggeredGridLayoutManager.VERTICAL){
                childCount = childCount -childCount%spanCount;
                if(pos >= childCount)
                    return true;
            }else {
                if((pos+1)%spanCount == 0){
                    return  true;
                }
            }
        }
        return false;
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int spanCount = getSpanCount(parent);
//        int chlidCount = parent.getAdapter().getItemCount();
//        Log.d("position",String.valueOf((int)parent.getChildItemId(view)));
//        if(isLastRaw(parent,(int)parent.getChildItemId(view),spanCount,chlidCount)){
//            outRect.set(0,0,divider.getIntrinsicWidth(),0);
//            Log.d("Item","ddddddddddddd");
//        }else if(isLastColum(parent,(int)parent.getChildItemId(view),spanCount,chlidCount)){
//            outRect.set(0,0,0,divider.getIntrinsicHeight());
//            Log.d("Item","ccccccccccccc");
//        }else {
//            outRect.set(0,0,divider.getIntrinsicWidth(),divider.getIntrinsicHeight());
//            Log.d("Item","aaaaaaaaa");
//        }
//    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int chlidCount = parent.getAdapter().getItemCount();
       Log.d("position",String.valueOf(itemPosition));
        if(isLastRaw(parent,itemPosition,spanCount,chlidCount)){
            outRect.set(0,0,divider.getIntrinsicWidth(),0);
            Log.d("Item","ddddddddddddd");
        }else if(isLastColum(parent,itemPosition,spanCount,chlidCount)){
            outRect.set(0,0,0,divider.getIntrinsicHeight());
            Log.d("Item","ccccccccccccc");
        }else {
            outRect.set(0,0,divider.getIntrinsicWidth(),divider.getIntrinsicHeight());
            Log.d("Item","aaaaaaaaa");
        }

    }
}
