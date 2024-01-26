package com.example.gamehang.screens


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamehang.R
import com.example.gamehang.constants.LanguageSpinner
import com.example.gamehang.ui.theme.GameHangTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameHangTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageNavigations()
                }
            }
        }
    }
}

var selectedLanguage = "Türkçe"


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController:NavController){
    val languages = listOf<String>("Türkçe","English")

    var expanded by remember { mutableStateOf(false) }
    Scaffold (
        topBar = {
           TopAppBar(
                title =
                {
                    Text(
                        text = "HANGMAN",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id =R.color.appbarcolor ),
                    titleContentColor = Color.White
                ),
               actions ={
                   LanguageSpinner(
                       choice =languages,
                       selectedLanguage = selectedLanguage,
                       onSelected = {
                           selectedLanguage = it
                       }
                   )
               }
                )
        },
        content = {
            padding->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize().padding(padding)

            )
            {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "",
                    modifier = Modifier.size(300.dp)
                )
                Text(text = "Oyuna Başlamak İçin Tıklayınız")
                Button(onClick = {
                                 navController.navigate("gameScreen")
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.appbarcolor),
                        contentColor = Color.White
                    )
                )
                {
                    Text(text = "Oyuna Başla")

                }


            }

        }
    )
}
@Composable
fun PageNavigations() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "mainScreen"
    )
    {
        composable("mainScreen") {
            MainScreen(navController = navController)
        }
        composable("gameScreen") {
            GameScreen(navController)
        }
    }
    }








