package alburraq.cartoon.me.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import alburraq.cartoon.me.R;


public class GlassesAdapter extends ArrayAdapter<String>{
		private final Activity context;
		private final Integer[] imageId;
		public GlassesAdapter(Activity context,String[] web, Integer[] imageId) 
		{
			super(context, R.layout.glasses_list_view_item, web);
			this.context = context;
			this.imageId = imageId;
		}
		@SuppressLint({ "ViewHolder", "InflateParams" })

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView= inflater.inflate(R.layout.glasses_list_view_item, null, true);

			ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView_glasses);
	
			imageView.setImageResource(imageId[position]);
			return rowView;
		}	
}