package com.plmt.boommall.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.plmt.boommall.R;
import com.plmt.boommall.entity.Category;
import com.plmt.boommall.entity.Goods;
import com.plmt.boommall.network.logic.GoodsLogic;
import com.plmt.boommall.ui.adapter.CategoryAdapter;
import com.plmt.boommall.ui.adapter.GoodsAdapter;
import com.plmt.boommall.ui.adapter.RVGoodsAdapter;
import com.plmt.boommall.ui.utils.MyItemClickListener;
import com.plmt.boommall.ui.view.recyclerviewflexibledivider.DividerItemDecoration;
import com.plmt.boommall.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryActivity extends Activity implements OnClickListener,
        MyItemClickListener {

	private Context mContext;
	private LinearLayout mSearchLl;
	private EditText mSearchEt;
	private ImageView mSearchIv;

	private ArrayList<Category> mCategoryList = new ArrayList<Category>();
	private ArrayList<Goods> mGoodsList = new ArrayList<Goods>();
	private CategoryAdapter mCategoryAdapter;
	private RVGoodsAdapter mRVGoodsAdapter;

	private ListView mGoodsLv;
	private GoodsAdapter mGoodsAdapter;

	private float y;
	private HashMap<String, Object> mAllMsgMap = new HashMap<String, Object>();
	private HashMap<String, Object> mSearchMsgMap = new HashMap<String, Object>();
	private HashMap<String, Object> mShowMsgMap = new HashMap<String, Object>();

	private long exitTime = 0;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {

			case GoodsLogic.CATEGROY_LIST_GET_SUC: {
				if (null != msg.obj) {
				}
				break;
			}
			case GoodsLogic.CATEGROY_LIST_GET_FAIL: {
				break;
			}
			case GoodsLogic.CATEGROY_LIST_GET_EXCEPTION: {
				break;
			}

			case GoodsLogic.GOODS_LIST_GET_SUC: {
				if (null != msg.obj) {

				}
				break;
			}
			case GoodsLogic.GOODS_LIST_GET_FAIL: {
				break;
			}
			case GoodsLogic.GOODS_LIST_GET_EXCEPTION: {
				break;
			}

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		mContext = CategoryActivity.this;
		initView();
		initData();
	}

	private void initView() {
		initHorizaontal();
		// initVertical();

		mSearchLl = (LinearLayout) findViewById(R.id.category_search_ll);

		mSearchIv = (ImageView) findViewById(R.id.category_search_iv);
		mSearchIv.setOnClickListener(this);

		mSearchEt = (EditText) findViewById(R.id.category_search_et);
		mSearchEt
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							// 此处为得到焦点时的处理内容
							mSearchLl.setVisibility(View.GONE);
							mSearchIv.setVisibility(View.VISIBLE);
						} else {
							// 此处为失去焦点时的处理内容
							mSearchEt.setText("");
							mSearchLl.setVisibility(View.VISIBLE);
							mSearchIv.setVisibility(View.GONE);
						}
					}
				});

		mSearchLl.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

			}
		});

	}

	private void initData() {
		mGoodsLv = (ListView) findViewById(R.id.recyclerview_vertical);
		mGoodsAdapter = new GoodsAdapter(mContext, mGoodsList);
		mGoodsLv.setAdapter(mGoodsAdapter);


		mGoodsLv.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// Log.i(TAG, "滚动状态变化");
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// Log.i(TAG, "正在滚动");
			}
		});

	}

	private void initHorizaontal() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_horizontal);

		for (int i = 0; i < 15; i++) {
			Category category = new Category();
			category.setPpid("分类" + i);
			category.setPpmc("http://img3.douban.com/view/commodity_story/medium/public/p19671.jpg");
			mCategoryList.add(category);
		}
		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		mCategoryAdapter = new CategoryAdapter(mCategoryList,
				R.layout.list_category_item);
		mCategoryAdapter.setOnItemClickListener(this);
		recyclerView.setAdapter(mCategoryAdapter);
		// recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
		// LinearLayoutManager.HORIZONTAL, mContext.getResources()
		// .getColor(R.color.transparent_background), SystemUtils
		// .dip2Px(mContext, 5)));

		// 设置Adapter
		// recyclerView.setAdapter(adapter);
	}

	public void initVertical() {
		//RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_vertical);
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recyclerview_horizontal);
		for (int i = 0; i < 10; i++) {
			Goods goods = new Goods();
			goods.setName("商品" + i);
			goods.setIconUrl("http://img3.douban.com/view/commodity_story/medium/public/p19671.jpg");
			mGoodsList.add(goods);
		}

		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 默认是Vertical，可以不写
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);

		// 创建Adapter，并指定数据集
		mRVGoodsAdapter = new RVGoodsAdapter(mGoodsList,
				R.layout.list_goods_item);
		mRVGoodsAdapter.setOnItemClickListener(this);
		recyclerView.setAdapter(mRVGoodsAdapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
				LinearLayoutManager.VERTICAL, mContext.getResources().getColor(
						R.color.gray_bg), SystemUtils.dip2Px(mContext, 20)));
		// 设置Adapter
		// recyclerView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshGoods();
	}

	private static void refreshGoods() {
	}

	private void search(String key) {
	}

	private void firstShowData(HashMap<String, Object> msgMap) {

	}

	private void refreshAllData(HashMap<String, Object> msgMap) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.category_search_iv: {
			if (!TextUtils.isEmpty(mSearchEt.getText().toString().trim())) {
				search(mSearchEt.getText().toString().trim());
			} else {
				Toast.makeText(mContext, getString(R.string.search_hint),
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onItemClick(View view, int postion) {
		Toast.makeText(mContext, "onItemClick:" + postion, Toast.LENGTH_SHORT)
				.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



}
