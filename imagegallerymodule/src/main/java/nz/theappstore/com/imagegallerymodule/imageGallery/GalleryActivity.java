package nz.theappstore.com.imagegallerymodule.imageGallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nz.theappstore.com.imagegallerymodule.R;
import nz.theappstore.com.imagegallerymodule.rxBus.PhotoUrlListBus;
import nz.theappstore.com.imagegallerymodule.rxBus.utils.PhotoEntity;
import rx.Observer;

/**
 * Created by Chike on 2/12/2017.
 */

public class GalleryActivity extends AppCompatActivity {

    ArrayList<PhotoEntity> datasetForGallery = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_gallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        final GalleryActivity.ImageGalleryAdapter adapter = new GalleryActivity.ImageGalleryAdapter(this, datasetForGallery);

        PhotoUrlListBus.getSpacePhotoObservable().subscribe(new Observer<List<PhotoEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<PhotoEntity> photoEntities) {
                datasetForGallery.addAll(photoEntities);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);

    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the layout
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            PhotoEntity photoEntity = mPhotoEntities.get(position);
            ImageView imageView = holder.mPhotoImageView;

            Glide.with(mContext)
                    .load(photoEntity.getUrl())
                    .placeholder(R.drawable.ic_cloud_off_red)
                    .into(imageView);
        }

        @Override
        public int getItemCount() {
            return (mPhotoEntities.size());
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    PhotoEntity photoEntity = mPhotoEntities.get(position);

                    Intent intent = new Intent(mContext, PhotoDetailActivity.class);
                    intent.putExtra(PhotoDetailActivity.EXTRA_SPACE_PHOTO, photoEntity);
                    startActivity(intent);
                }
            }
        }

        private ArrayList<PhotoEntity> mPhotoEntities;
        private Context mContext;

        public ImageGalleryAdapter(Context context, ArrayList<PhotoEntity> photoEntities) {
            mContext = context;
            mPhotoEntities = photoEntities;
        }
    }
}
