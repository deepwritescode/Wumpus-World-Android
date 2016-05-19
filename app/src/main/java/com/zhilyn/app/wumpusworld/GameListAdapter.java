package com.zhilyn.app.wumpusworld;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 5/6/16.
 * adapter for the game tiles
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ListItem> {

    private static final int TYPE_HIDDEN = 0;
    private static final int TYPE_SHOWN = 1;

    private GameMap map;

    private List<Block> mData;
    private RecyclerView recyclerView;

    public GameListAdapter(){
        mData = new ArrayList<>();
        this.map = GameMap.init();
        mData.removeAll(mData);
        mData.addAll(map.getListOfBlocks());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public ListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item_game_block, parent, false);
        return new ListItem(v);
    }

    @Override
    public int getItemViewType(int position) {
        Block b = mData.get(position);
        if (b.hasPlayer() || b.isShown()){
            return TYPE_SHOWN;
        }

        return TYPE_HIDDEN;
    }

    @Override
    public void onBindViewHolder(ListItem holder, int position) {
        int viewType = getItemViewType(position);
        holder.itemView.setOnClickListener(itemClickListener);

        switch (viewType){
            case TYPE_HIDDEN:
                //holder.mText.setText("H");
                holder.container.setBackgroundResource(R.drawable.bg_card_dark);
                return;
            case TYPE_SHOWN:
                //holder.mText.setText("S");
                break;
        }

        Block b = mData.get(position);
        List<GamePiece> pieces = b.getPieces();
        //holder.mText.append(b.getPoint().toString());

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

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = recyclerView.getChildAdapterPosition(v);
            Log.e("Position", ""+pos);
        }
    };


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ListItem extends RecyclerView.ViewHolder{
        final TextView mText;
        final ImageView gold;
        final ImageView glitter;
        final ImageView breeze;
        final ImageView player;
        final ImageView pit;
        final ImageView wumpus;
        final ImageView stench;
        final FrameLayout container;

        public ListItem(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);

            container = (FrameLayout) itemView.findViewById(R.id.container);
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