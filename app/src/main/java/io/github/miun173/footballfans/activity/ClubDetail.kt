package io.github.miun173.footballfans.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.github.miun173.footballfans.R
import org.jetbrains.anko.*

class ClubDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_detail)

        val image = intent.getIntExtra("image", R.drawable.img_chelsea)
        val name = intent.getStringExtra("name")
        val details = intent.getStringExtra("detail")

        verticalLayout {
            lparams(width = matchParent, height = wrapContent){
                horizontalGravity = 1
                padding = dip(10)
                marginStart = dip(5)
            }

            imageView(image).lparams(width = dip(100), height = dip(100))

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

    }

}


