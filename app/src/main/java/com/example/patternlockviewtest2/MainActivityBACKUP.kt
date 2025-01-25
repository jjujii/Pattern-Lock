package com.example.patternlockviewtest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import io.paperdb.Paper

class MainActivityBACKUP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)

    }
}
