package chatapp.magnus.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import chatapp.magnus.chatapp.ListFragment.OnListFragmentInteractionListener;
import chatapp.magnus.chatapp.posts.ContentItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ContentItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ContentItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private StorageReference mStorageRef;
    Context context;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public MyItemRecyclerViewAdapter(List<ContentItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        context = parent.getContext();
        return new ViewHolder(view);
    }

//    public final String id;
//    public final String content;
//    public final String title;
//    public final int votes;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mVotesView.setText(mValues.get(position).votes+"");

        //Noget der loader string om til et billede her
        String picid = mValues.get(position).content;

        Picasso.with(context)
                .load(picid)
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.mImageView);

        holder.mUpvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentItem cont = holder.mItem;
                cont.votes++;
                myRef.child("memes").child(mValues.get(position).id).setValue(cont);
//                holder.mVotesView.setText(mValues.get(position).votes+"");
            }
        });

        holder.mDownvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentItem cont = holder.mItem;
                cont.votes--;
                myRef.child("memes").child(mValues.get(position).id).setValue(cont);
//                holder.mVotesView.setText(mValues.get(position).votes+"");
            }
        });

        myRef.child("memes").child(mValues.get(position).id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.mItem = dataSnapshot.getValue(ContentItem.class);
                holder.mVotesView.setText(holder.mItem.votes+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        holder.mImageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mVotesView;
        public final ImageView mImageView;
        public final ImageView mUpvoteButton;
        public final ImageView mDownvoteButton;
        public ContentItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.contentTitle);
            mVotesView = (TextView) view.findViewById(R.id.contentVotes);
            mImageView = (ImageView) view.findViewById(R.id.imageContent);
            mUpvoteButton = (ImageView) view.findViewById(R.id.imageUpvote);
            mDownvoteButton = (ImageView) view.findViewById(R.id.imageDownvote);



        }

        @Override
        public String toString() {
            return super.toString() + " '" + mVotesView.getText() + "'";
        }
    }
}


























