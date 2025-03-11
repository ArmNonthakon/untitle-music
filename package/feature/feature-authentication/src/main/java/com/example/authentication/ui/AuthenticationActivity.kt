package com.example.authentication.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.controller.AuthenticationController
import com.example.core.storage.SecureSharedPreferences
import kotlinx.coroutines.runBlocking
import java.time.Instant

class AuthenticationActivity : ComponentActivity() {
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        val secureStorage = SecureSharedPreferences.getInstance(context = baseContext)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(secureStorage.getString("access_token", null) != null &&  Instant.now().epochSecond <= secureStorage.getLong("expires_in", 0) ){
            println(secureStorage.getString("expires_in", null))
            val intent = Intent().apply {
                setClassName("com.example.untitledmusic", "com.example.untitledmusic.ui.AppActivity")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }else{
            secureStorage.edit().clear()
            setContent {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = Color(35, 35, 35))) {
                        val context = LocalContext.current
                        val configuration = LocalConfiguration.current
                        val widgetScreen = configuration.screenWidthDp.dp
                        val authenUrl = "https://accounts.spotify.com/en/authorize?client_id=ce98505416dc45cc92e85778734e85a4&response_type=code&redirect_uri=untitledmusic://callback&scope=user-top-read%20user-read-playback-state%20app-remote-control"

                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                            Image(painter = painterResource(id = com.example.core.R.drawable.spotify_logo), contentDescription = "", modifier = Modifier.size(width = 370.dp, height = 300.dp))
                            Button(
                                modifier = Modifier.size(width = widgetScreen - (widgetScreen / 5), height = 50.dp),
                                onClick = {
                                    val customTabsIntent = CustomTabsIntent.Builder().build()
                                    customTabsIntent.launchUrl(context, Uri.parse(authenUrl))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(15.dp)
                            ) {
                                Text(text = "Login with Spotify", color = Color.Black, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W700))
                            }
                            Spacer(Modifier.size(3.dp))
                            TextButton(onClick = {}) {
                                Text("Do you have a Spotify account?", color = Color.White, style = TextStyle(fontSize = 14.sp))
                            }
                        }
                    }
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        val controller = AuthenticationController(context = baseContext)
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith("untitledmusic://callback")) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                runBlocking {
                    println("code : $code")
                    controller.getAccessToken(code = code)
                    val intent = Intent().apply {
                        setClassName("com.example.untitledmusic", "com.example.untitledmusic.ui.AppActivity")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}

