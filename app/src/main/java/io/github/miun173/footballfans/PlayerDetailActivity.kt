package io.github.miun173.footballfans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.model.Player
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val player = intent.getParcelableExtra<Player>(getString(R.string.intent_player)) ?: Player()

        tv_desc.text = player.strDescriptionEN ?: ""
        tv_name.text = player.strPlayer ?: ""
        Picasso.get()
                .load(player.strFanart1 ?: player.strThumb)
                .placeholder(R.drawable.img_placholder)
                .error(R.drawable.img_error)
                .into(imv_pict)
    }
}
