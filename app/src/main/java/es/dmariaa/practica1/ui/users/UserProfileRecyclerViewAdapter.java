package es.dmariaa.practica1.ui.users;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.data.model.UserProfile;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserProfile}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserProfileRecyclerViewAdapter extends RecyclerView.Adapter<UserProfileRecyclerViewAdapter.ViewHolder> {

    private List<UserProfile> userProfileList;
    private Context context;
    private UserProfileListFragment fragment;

    public UserProfileRecyclerViewAdapter(List<UserProfile> items, UserProfileListFragment fragment) {
        this.userProfileList = items;
        this.fragment = fragment; 
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserProfile userProfile = userProfileList.get(position);
        Bitmap avatar = this.getAvatarBitmap(userProfile.getPhoto());

        if(avatar != null) {
            holder.mAvatar.setImageBitmap(avatar);
            holder.mAvatar.setVisibility(View.VISIBLE);
            holder.mPlaceholder.setVisibility(View.INVISIBLE);
        } else {
            holder.mPlaceholder.setText(userProfile.getInitials());
            holder.mPlaceholder.setBackground(getAvatarBackground(userProfile));
            holder.mAvatar.setVisibility(View.INVISIBLE);
            holder.mPlaceholder.setVisibility(View.VISIBLE);
        }

        holder.mName.setText(userProfile.getDisplayName());
        holder.mLastMatch.setText(userProfile.getBirthDate().toString());
    }

    private Drawable getAvatarBackground(UserProfile profile) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.circle);
        drawable.setColorFilter(profile.getProfileColor(), PorterDuff.Mode.MULTIPLY);
        return drawable;
    }

    /**
     * Inspired from:
     * https://android.jlelse.eu/avatarview-custom-implementation-of-imageview-4bcf0714d09d
     * @param avatar
     * @return
     */
    private Bitmap getAvatarBitmap(String avatar) {
        try {
            String imageFile = String.format("question-images/%s", avatar);
            InputStream questionImage = this.context.getResources().getAssets().open(imageFile);
            Bitmap questionImageBitmap = BitmapFactory.decodeStream(questionImage);
            return questionImageBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return userProfileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mAvatar;
        public final TextView mName;
        public final TextView mLastMatch;
        public final TextView mPlaceholder;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mName = (TextView) view.findViewById(R.id.name);
            mLastMatch = (TextView) view.findViewById(R.id.last_match);
            mPlaceholder = (TextView) view.findViewById(R.id.placeholder);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLastMatch.getText() + "'";
        }
    }
}