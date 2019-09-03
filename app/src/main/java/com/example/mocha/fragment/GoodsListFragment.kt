package com.example.mocha.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.mocha.adpter.goodslist.ScrollLeftAdapter
import com.example.mocha.adpter.goodslist.ScrollRightAdapter
import com.example.mocha.base.BaseFragment
import com.example.mocha.data.ClassEntity
import com.example.mocha.data.StudentEntity
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_goods_list.*


/**
 * 作者 : mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/19 15:03
 * 功能 :
 */
class GoodsListFragment : BaseFragment() {


    private lateinit var scrollLeftAdapter: ScrollLeftAdapter
    //子标签的数据集合
    private var studentList: ArrayList<StudentEntity> = ArrayList()
    //父标签的数据集合
    private var classAllList: ArrayList<ClassEntity> = ArrayList()

    //目标项是否在最后一个可见项之后
    private var mShouldScroll: Boolean = false
    //记录目标项位置
    private var mToPosition: Int = 0

    companion object {
        fun newInstance(params: Bundle?): GoodsListFragment {
            val frag = GoodsListFragment()
            params?.let {
                frag.arguments = it
            }
            return frag
        }
    }

    override fun initView() {
        super.initView()
        setupData()
        //左边一级列表的adapter
        scrollLeftAdapter = ScrollLeftAdapter(context!!, classAllList)
    }

    override fun initListener() {
        super.initListener()

        //左边一级列表的点击事件，点击滑动右边的子列表到指定的未知
        scrollLeftAdapter.setOnItemViewClickListener(object : ScrollLeftAdapter.OnItemClickListener {
            override fun onItemClick(item: ClassEntity, i: Int) {
                Log.d("tag10", "左边索引$i")
                scrollLeftAdapter.setSelection(i)
                //临时变量记录当前左侧选中条目的子集数据长度
                var sum = 0
                for (index in 0 until i) {
                    sum += classAllList[index].list!!.size
                }
                Log.d("tag10", "sum$sum")
                //根据左侧，定位右侧的展示数据
                (goods_list_right.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(sum, 0)
//                goods_list_right.smoothScrollToPosition(sum)
//                smoothMoveToPosition(goods_list_right,sum)
            }

        })

        //右边列表滑动的监听，滑动到第二级列表就移动左边的父级标题
        goods_list_right.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //获取滚动时的第一条展示的position
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

                Log.d("tag10", "firstVisibleItemPosition$firstVisibleItemPosition")
                //获取右侧数据的关联id
                val studentModel = studentList[firstVisibleItemPosition]
                val outId = studentModel.outId
                //记录外部id， 更新左侧状态栏状态
                var pos = 0

                for (index in 0 until classAllList.size) {
                    val id = classAllList[index].id
                    if (outId == id) {
                        pos = index
                    }
                }
                Log.d("tag10", "pos$pos")
                scrollLeftAdapter.setSelection(pos)

            }
        })
    }

    override fun initData() {
        super.initData()

        goods_list_left.adapter = scrollLeftAdapter
        goods_list_left.layoutManager = LinearLayoutManager(context)

        scrollLeftAdapter.setSelection(0)

        goods_list_right.adapter = ScrollRightAdapter(context!!, studentList)
        goods_list_right.layoutManager = LinearLayoutManager(context)


    }


    /**
     * 滑动到指定位置
     */
    private fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position)
            mToPosition = position
            mShouldScroll = true
        }
    }

    /**
     * 制造假数据
     */
    private fun setupData() {

        val gson = Gson()

        var classList: ArrayList<ClassEntity> = ArrayList()
        var studentList1: ArrayList<StudentEntity> = ArrayList()
        var studentList2: ArrayList<StudentEntity> = ArrayList()
        var studentList3: ArrayList<StudentEntity> = ArrayList()

        for (i in 0 until 15) {
            var studentModel = StudentEntity(("小明$i"), i, 1)
            studentList1.add(studentModel)
        }

        for (i in 0 until 20) {
            var studentModel = StudentEntity(("小红$i"), i, 2)
            studentList2.add(studentModel)
        }

        for (i in 0 until 20) {
            var studentModel = StudentEntity(("小龙$i"), i, 3)
            studentList3.add(studentModel)
        }

        var classModel = ClassEntity(1, "一年级", studentList1)

        var classMode2 = ClassEntity(2, "二年级", studentList2)

        var classMode3 = ClassEntity(3, "三年级", studentList3)

        classList.add(classModel)
        classList.add(classMode2)
        classList.add(classMode3)

        val jsonStr = gson.toJson(classList)
        Log.e("tag10", jsonStr)

        /**
         * 解析数据
         * */

        //Json的解析类对象
        val parser = JsonParser()
        //将JSON的String 转成一个JsonArray对象
        val jsonArray = parser.parse(jsonStr).asJsonArray

//        val userBeanList = mutableListOf<ClassEntity>()

        //加强for循环遍历JsonArray
        for (user in jsonArray) {
            //使用GSON，直接转成Bean对象
            val userBean = gson.fromJson<ClassEntity>(user, ClassEntity::class.java)
            classAllList.add(userBean)
        }

        /**
         * 设置默认数据
         * */
        for (index in classAllList.indices) {
            val list = classAllList[index].list
            list?.let { studentList.addAll(it) }
        }
//        val list = classAllList[0].list
//        list?.let {
//            studentList.addAll(it)
//        }
        classAllList.forEach {
            Log.e("tag10", "classAllList: $it")
        }
        studentList.forEach {
            Log.e("tag10", "studentList: $it")
        }
    }

    override fun getLayoutId(): Int = com.example.mocha.R.layout.fragment_goods_list


}