package com.shuangduan.zcy.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.utils.PathUtils;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/26
 *     desc   : 用于增减图片盛放容器
 *     version: 1.0
 * </pre>
 */

public class PicContentView extends RecyclerView {

    private Context context;
    private List<PicContentBean> list = new ArrayList<>();
    private PicAdapter picAdapter;
    private OnClickListener listener;
    private boolean couldAdd = true;
    private int max = 6;

    public PicContentView(Context context) {
        this(context, null);
    }

    public PicContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicContentView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PicContentView, defStyle, 0);
        max = typedArray.getInt(R.styleable.PicContentView_maxSize, 6);
        typedArray.recycle();

        list.add(new PicContentBean(null, null, 0));
        picAdapter = new PicAdapter(list);
        this.setLayoutManager(new GridLayoutManager(context, 3));
        this.setAdapter(picAdapter);
    }

    public List<PicContentBean> getList() {
        return list;
    }

    /**
     * 插入多张图片
     * @param list
     */
    public void insert(List<PicContentBean> list) {
        withRx(list);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    class PicAdapter extends RecyclerView.Adapter<Holder>{

        private List<PicContentBean> list;

        public PicAdapter(List<PicContentBean> list) {
            this.list = list;
        }

        public void insertData(PicContentBean picContentBean){
            //能增加图片
            if (couldAdd){
                int position = this.list.size() - 1;
                this.list.add(position, picContentBean);
                if (this.list.size() > max){
                    couldAdd = false;
                    this.list = this.list.subList(0,max);
                }

                notifyDataSetChanged();
            }
        }

        public void insertData(List<PicContentBean> list){
            //能增加图片
            if (couldAdd){
                int position = this.list.size() - 1;
                this.list.addAll(position, list);
                if (this.list.size() > max){
                    couldAdd = false;
                    this.list = this.list.subList(0,max);
                }

                notifyDataSetChanged();
            }
        }

        private void deleteData(int position){
            if (this.list.size() == max && !couldAdd){
                this.list.remove(position);
                this.list.add(new PicContentBean(null, null, 0));
                couldAdd = true;
                notifyItemRemoved(position);
                notifyItemInserted(max);
            }else {
                this.list.remove(position);
                couldAdd = true;
                notifyItemRemoved(position);
            }
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_rv_pic, parent, false);
            return new Holder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int position) {
            //除最后一个item外，均设置图片
            if (couldAdd){
                if (position != (list.size() - 1)){
                    if (list.get(position).getUri() != null){//uri不为空显示本地图片
                        holder.ivPic.setImageURI(list.get(position).getUri());
                    }else {
                        //path不为空显示网络图片
                        ImageLoader.load(getContext(), new ImageConfig.Builder().url(list.get(position).getPath()).imageView(holder.ivPic).build());
                    }
                    holder.ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.ivDelete.setVisibility(VISIBLE);
                }else {
                    holder.ivPic.setImageResource(R.drawable.default_pic);
                    holder.ivPic.setScaleType(ImageView.ScaleType.CENTER);
                    holder.ivDelete.setVisibility(GONE);
                }
            } else {
                //当数据为max，图片数量达到max时，增加按钮消失
                if (list.get(position).getUri() != null){//uri不为空显示本地图片
                    holder.ivPic.setImageURI(list.get(position).getUri());
                }else {
                    //path不为空显示网络图片
                    ImageLoader.load(getContext(), new ImageConfig.Builder().url(list.get(position).getPath()).imageView(holder.ivPic).build());
                }
                holder.ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.ivDelete.setVisibility(VISIBLE);
            }


            holder.ivPic.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                if (pos == list.size() - 1 && couldAdd){
                    listener.add();
                }else {
                    listener.see(holder.ivPic, pos);
                }
            });

            holder.ivDelete.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                listener.delete(pos);
                deleteData(pos);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public List<PicContentBean> getList() {
            return list;
        }
    }

    class Holder extends BaseViewHolder{

        ImageView ivPic;
        ImageView ivDelete;

        Holder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }

    }

    public interface OnClickListener{
        void add();
        void see(View view, int position);
        void delete(int pos);
    }

    public static class PicContentBean{
        /**
         * 用于本地图片uri
         */
        private Uri uri;
        /**
         * 用于网络图片url
         */
        private String path;
        /**
         * 网络图片的id
         */
        private int id;

        public PicContentBean(Uri uri, String path, int id) {
            this.uri = uri;
            this.path = path;
            this.id = id;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    /**
     * 多张图片同时压缩容易OOM,采用单张依次压缩
     * @param photo
     */
    @SuppressLint("CheckResult")
    private void withRx(final List<PicContentBean> photo) {
        //如果uri为空，代表为网络图片，无需压缩，直接增加即可
        Flowable.just(photo)
                .observeOn(Schedulers.io())
                .map(list -> {
                    List<String> pics = new ArrayList<>();
                    for (PicContentBean pic : list) {
                        if (pic.getUri() == null){
                            picAdapter.insertData(pic);
                        }else {
                            pics.add(PathUtils.getPath(context, pic.getUri()));
                        }
                    }

                    return Luban.with(context)
                            .setTargetDir(getPath())
                            .load(pics)
                            .get();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    ToastUtils.showShort(throwable.getMessage());
                    LogUtils.i(throwable.getMessage());
                })
                .onErrorResumeNext(Flowable.<List<File>>empty())
                .subscribe(list -> {
                    for (File file : list) {
                        showResult(file);

                        Uri uri = Uri.fromFile(file);
                        LogUtils.i(uri);

                        picAdapter.insertData(new PicContentBean(uri, null, 0));

                    }
                }, throwable -> {
                     LogUtils.i(throwable.getMessage());
                    ToastUtils.showShort("图片过大，请一张一张上传");
                });

    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void showResult(File file) {
//        int[] originSize = computeSize(photos.get(0));
        int[] thumbSize = computeSize(file);
//        String originArg = String.format(Locale.CHINA, "原图参数：%d*%d, %dk", originSize[0], originSize[1], photos.get(0).length() >> 10);
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);

        LogUtils.i(thumbArg);
    }

    private int[] computeSize(File srcImg) {
        int[] size = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;

        return size;
    }

}
