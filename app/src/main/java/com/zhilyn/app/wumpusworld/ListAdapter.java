package com.zhilyn.app.wumpusworld;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 5/2/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItem> {

    private List<Block> mData;

    public ListAdapter(){
        mData = new ArrayList<>();
        GameMap map = GameMap.init();
        Block[][] grid = map.getGrid();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Block b = grid[x][y];
                mData.add(b);
            }
        }
    }

    @Override
    public ListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item, parent, false);
        return new ListItem(v);
    }

    @Override
    public void onBindViewHolder(ListItem holder, int position) {
        Block b = mData.get(position);
        List<GamePiece> pieces = b.getPieces();
        for (GamePiece piece : pieces) {
            holder.mText.append(piece.toString()+"\n");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ListItem extends RecyclerView.ViewHolder{
        private final TextView mText;

        public ListItem(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
