package weiyuan.searchviewtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	
	private Context mContext;
    private List<? extends Map<String,?>> mDataList;
    private List<Map<String,?>> mOriginalDataList;
	
	private int mResource;
	private String[] mFrom;
	private int[] mTo;
	
	private ViewHolder mViewHolder;
	private LayoutInflater mInflater;
	private boolean mNotifyChanged = true;
	
	private MyFilter mFilter;
	
	
	
	//A holder contains all display elements for re-use
	private class ViewHolder{
		ImageView imageView;
		TextView textView1;
		TextView textView2;
		
	}
	
	
	public MyAdapter(Context context, List<? extends Map<String,?>> dataList, int resource, String[] from, int[] to){
		mContext = context;
		mDataList = dataList;
		mResource = resource;
		mFrom = from;
		mTo = to;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int posotion) {
		return posotion;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 
		
		View rowView = convertView;
		
		if(rowView==null)
		{
			rowView = mInflater.inflate(mResource, parent,false);
			mViewHolder = new ViewHolder();
			mViewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView1);
			mViewHolder.textView1 = (TextView) rowView.findViewById(R.id.textView1);
			mViewHolder.textView2 = (TextView) rowView.findViewById(R.id.textView2);
			
			rowView.setTag(mViewHolder);
		}
		
			
		mViewHolder = (ViewHolder) rowView.getTag();
		//mViewHolder.imageView.setImageResource((Integer) mDataList.get(position).get(mFrom[0]));
		mViewHolder.textView1.setText((CharSequence) mDataList.get(position).get(mFrom[0]));
		mViewHolder.textView2.setText((CharSequence) mDataList.get(position).get(mFrom[1]).toString());
		
		
		
		
		return rowView;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		mNotifyChanged = true;
		
	}
	
	public void sort(Comparator<Map<String,?>> comparator)
	{
		Collections.sort(mDataList, comparator);
		if (mNotifyChanged) notifyDataSetChanged();
	}
	
	/**
	 * @return the mFilter
	 */
	public MyFilter getFilter() {
		if(mFilter ==null)
			mFilter = new MyFilter();
		return mFilter;
	}



	class MyFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence query) {
			//System.out.println("Filter Test");
			
			FilterResults results = new FilterResults();
			
			if(mOriginalDataList == null) {
				mOriginalDataList = new ArrayList<Map<String,?>>(mDataList);
			}
			
			//System.out.println(mDataList);
			//System.out.println(mOriginalDataList);
			if(query==null || query.length()==0){
				
				ArrayList<Map<String,?>> list = (ArrayList<Map<String, ?>>) mOriginalDataList;
				results.values = list;
				results.count = list.size();
				
				
			}
			else{
				ArrayList<Map<String,?>> list = (ArrayList<Map<String, ?>>) mOriginalDataList;
				int count = list.size();
				ArrayList<Map<String,?>> filteredList = new ArrayList<Map<String,?>>(count);
				
				String queryString = query.toString().toLowerCase(Locale.ENGLISH);
				
				
				
				for(int i=0; i<count; i++)
				{
					HashMap<String,?> map = (HashMap<String, ?>) list.get(i);
					
					if(map!=null){
						
						String location = (String) map.get(mFrom[0]);
						String[] words = location.split(" ");
						
						for(int j=0; j<words.length;j++)
						{
							String word = words[j];
					
							if(word.toLowerCase(Locale.ENGLISH).startsWith(queryString)){
								
								filteredList.add(map);
								
								
								
							}
						}
					}
				}
			
			
			
				results.values = filteredList;
				results.count = filteredList.size();
				
			}
			
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence query, FilterResults results) {
			 mDataList = (List<? extends Map<String,?>>) results.values;
	            if (results.count > 0) {
	                notifyDataSetChanged();
	            } else {
	                notifyDataSetInvalidated();
	            }
			
		}
		
		
	}
	

}
