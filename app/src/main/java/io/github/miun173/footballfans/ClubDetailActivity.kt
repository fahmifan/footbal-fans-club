package io.github.miun173.footballfans

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.miun173.footballfans.R
import kotlinx.android.synthetic.main.item_list.view.*
import org.jetbrains.anko.*

class ClubDetailActivity : AppCompatActivity() {
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_detail)

        val image = intent.getIntExtra("image", 0)
        val name = intent.getStringExtra("name")
        val details = intent.getStringExtra("detail")

        verticalLayout {
            imageView = imageView()
                    .lparams(width = dip(100), height = dip(100))

            lparams(width = matchParent, height = wrapContent){
                horizontalGravity = 1
                padding = dip(10)
                marginStart = dip(5)
            }

//            imageView(image).lparams(width = dip(100), height = dip(100))

            textView(name) {
                textSize = sp(6).toFloat()
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                padding = dip(5)
            }

            textView(details) {
                textSize = sp(5).toFloat()
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                padding = dip(5)
            }
        }

        Picasso.get()
                .load(intent.getIntExtra("image", R.drawable.img_chelsea))
                .into(imageView)

    }

}


