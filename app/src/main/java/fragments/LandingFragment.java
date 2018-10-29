package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.backyard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by applify on 9/29/2017.
 */

public class LandingFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    View itemView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_landing, container, false);
        ButterKnife.bind(LandingFragment.this, itemView);

        initUI();
        initListener();

        return itemView;
    }

    private void initUI() {

    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
