package alburraq.cartoon.me.adapter;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import alburraq.cartoon.me.R;


public class AlbumViewPagerAdapter extends PagerAdapter {
    // Declare Variables
	File [] images;
	LayoutInflater inflater;
	Context context;
    public AlbumViewPagerAdapter(Context context,File []images) {
       this.images=images;
       this.context=context;
    }
 
    @Override
    public int getCount() {
        return images.length;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
 
    @SuppressWarnings("deprecation")
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
 
        // Declare Variables
        ImageView album_image;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager_album_item, container,
                false);
 
 
        // Locate the ImageView in viewpager_item.xml
        album_image = (ImageView) itemView.findViewById(R.id.imageView_album_activity);
        // Capture position and set to the ImageView
        album_image.setBackgroundDrawable(Drawable.createFromPath(""+images[position]));
 
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }
}
