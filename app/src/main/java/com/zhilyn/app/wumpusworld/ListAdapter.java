package com.zhilyn.app.wumpusworld;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        for (int y = 3; y >= 0; y--) {
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
        holder.mText.append(b.getPlot().toString());
        for (GamePiece piece : pieces) {
            switch (piece.getType()){
                case PLAYER:
                    holder.player.setVisibility(View.VISIBLE);
                    break;
                case PIT:
                    holder.pit.setVisibility(View.VISIBLE);
                    break;
                case WUMPUS:
                    holder.wumpus.setVisibility(View.VISIBLE);
                    break;
                case BREEZE:
                    holder.breeze.setVisibility(View.VISIBLE);
                    break;
                case STENCH:
                    holder.stench.setVisibility(View.VISIBLE);
                    break;
                case GLITTER:
                    holder.glitter.setVisibility(View.VISIBLE);
                    break;
                case GOLD:
                    holder.gold.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void solve() {

    }

    public class ListItem extends RecyclerView.ViewHolder{
        private final TextView mText;
        private final ImageView gold;
        private final ImageView glitter;
        private final ImageView breeze;
        private final ImageView player;
        private final ImageView pit;
        private final ImageView wumpus;
        private final ImageView stench;

        public ListItem(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);

            pit = (ImageView) itemView.findViewById(R.id.pit);
            gold = (ImageView) itemView.findViewById(R.id.gold);
            breeze = (ImageView) itemView.findViewById(R.id.breeze);
            player = (ImageView) itemView.findViewById(R.id.player);
            wumpus = (ImageView) itemView.findViewById(R.id.wumpus);
            stench = (ImageView) itemView.findViewById(R.id.stench);
            glitter = (ImageView) itemView.findViewById(R.id.glitter);
        }
    }
}
