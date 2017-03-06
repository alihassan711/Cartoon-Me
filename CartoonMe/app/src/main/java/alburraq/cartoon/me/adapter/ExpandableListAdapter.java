/*
 * Developed By Muhammad Ali Hassan
 * alihassan711@gmail.com
 * http://www.al-burraq.com
 *
 */

package alburraq.cartoon.me.adapter;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import alburraq.cartoon.me.R;

//import com.nostra13.universalimageloader.core.ImageLoader;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    boolean showcase_flag=false;
    boolean dropdown_flag=false;
    private Activity activity;
    final int index = 0;
//    ImageLoader imageLoader;
//    ShowcaseView.ConfigOptions co;
//    ShowcaseView sv;
    private ArrayList<Bitmap> images;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
 
    public ExpandableListAdapter(Activity act,Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData) {
        this._context = context;
        this.activity=act;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
//        this.imageLoader = ImageLoader.getInstance();
        images = new ArrayList<Bitmap>();

        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.help));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.share_icon));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.about_us));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.contact_us));

        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_1));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_2));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_3));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_4));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_5));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.cartoon_6));
      
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_list_item, null);
        }
        ImageView view = (ImageView) convertView.findViewById(R.id.imageview_dropdown);
        view.setVisibility(View.GONE);
        TextView txtListChild = (TextView) convertView.findViewById(R.id.title);
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "Roboto-Light.ttf");
        txtListChild.setTypeface(tf);
        txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	if(groupPosition<4)
    		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    	else
    		return 0;
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
    	if(_listDataHeader!=null)
    		return this._listDataHeader.size();
    	else
    		return 0;
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @SuppressLint("InflateParams")
	@Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
    
    	
        String headerTitle = (String) getGroup(groupPosition);

            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(groupPosition<10)
            {
            	convertView = infalInflater.inflate(R.layout.drawer_list_item, null);
                ImageView img = (ImageView) convertView.findViewById(R.id.icon);
                ImageView dropdown = (ImageView) convertView.findViewById(R.id.imageview_dropdown);
               
                dropdown.setVisibility(View.GONE);
                TextView lblListHeader = (TextView) convertView.findViewById(R.id.title);
                Typeface tf = Typeface.createFromAsset(activity.getAssets(), "Roboto-Light.ttf");
                lblListHeader.setTypeface(tf);
            	lblListHeader.setText(headerTitle);
                if(groupPosition>3){
                    img.setMaxHeight(450);
                    img.getLayoutParams().height = 400;
                }
            	img.setImageBitmap(images.get(groupPosition));
            }
            else
            {
            	convertView = infalInflater.inflate(R.layout.adapter_image_row, null);
                final ImageView img = (ImageView) convertView.findViewById(R.id.icon);
//            	DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
//				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(_context).
//			            defaultDisplayImageOptions(displayimageOptions).build();
//				imageLoader.init(config);
//				if(Constants.category_index==0)
//				{
//					String url = "http://" + Constants.cartoon_thumbnails[groupPosition-5];
//					imageLoader.displayImage(url, img, displayimageOptions, new ImageLoadingListener() {
//						
//						@Override
//						public void onLoadingStarted() {
//							// TODO Auto-generated method stub
//							setBar();
//						}
//						
//						@Override
//						public void onLoadingFailed(FailReason arg0) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onLoadingComplete(Bitmap arg0) {
//							// TODO Auto-generated method stub
//							hideBar();
//						}
//						
//						@Override
//						public void onLoadingCancelled() {
//							// TODO Auto-generated method stub
//							
//						}
//					});
//				}
//
//					String url2 = "http://" + Constants.all_thumbnails[groupPosition-4];
//                    Log.e("URL",""+url2);
//					imageLoader.displayImage(url2, img, displayimageOptions, new ImageLoadingListener() {
//
//                        @Override
//                        public void onLoadingStarted(String imageUri, View view) {
//                            setBar();
//                        }
//                        @Override
//                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                        }
//                        @Override
//                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                            hideBar();
//                        }
//                        @Override
//                        public void onLoadingCancelled(String imageUri, View view) {
//                        }
//					});
				
//				if(Constants.category_index==2)
//				{
//					String url = "http://" + Constants.girl_thumbnails[groupPosition-5];
//					imageLoader.displayImage(url, img, displayimageOptions, new ImageLoadingListener() {
//						
//						@Override
//						public void onLoadingStarted() {
//							// TODO Auto-generated method stub
//							setBar();
//						}
//						
//						@Override
//						public void onLoadingFailed(FailReason arg0) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onLoadingComplete(Bitmap arg0) {
//							// TODO Auto-generated method stub
//							hideBar();
//						}
//						
//						@Override
//						public void onLoadingCancelled() {
//							// TODO Auto-generated method stub
//							
//						}
//					});
//				}
              
            }
        return convertView;
    }
    public void setBar()
    {
    	activity.setProgressBarIndeterminateVisibility(true);
    	
    }
    public void hideBar()
    {
    	activity.setProgressBarIndeterminateVisibility(false);
    }
    @Override
    public boolean hasStableIds() {
        return false;
       
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

