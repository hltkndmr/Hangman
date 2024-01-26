package com.example.gamehang.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gamehang.R
import com.example.gamehang.functions.getRandomWord
import com.example.gamehang.functions.getWordListFromXml


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController) {


    val context = LocalContext.current
    val wordList = remember { getWordListFromXml(context) }
    var inputText by remember { mutableStateOf(TextFieldValue()) }
    var clickedCharacters by remember { mutableStateOf(emptyList<Char>()) }
    var incorrectGuessCount by remember { mutableStateOf(0) }


    val hangmanImages = listOf(
        R.drawable.hangman_default,
        R.drawable.c,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i,
        R.drawable.j,
        R.drawable.k
    )
    val selectedWord = remember { getRandomWord(wordList) }
    var gameInProgress by remember { mutableStateOf(true) }
    var outOfAttempts by remember { mutableStateOf(false) }

    fun handleGuess()
    {
        if (!gameInProgress)
        {
            return
        }
        val guess = inputText.text.firstOrNull()?.lowercaseChar() ?: return
        if (!clickedCharacters.contains(guess))
        {
            clickedCharacters = clickedCharacters + guess

            if (!selectedWord.contains(guess))
            {
                incorrectGuessCount++
            }
            if (!selectedWord.any { it !in clickedCharacters })
            {
                gameInProgress = false
                outOfAttempts = false
            }
            else if (incorrectGuessCount == hangmanImages.size - 1)
            {
                gameInProgress = false
                outOfAttempts = true
            }
        }

        inputText = TextFieldValue("")
    }
    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     title =
                     {
                         Text(
                             text = "HANGMAN",
                             fontSize = 32.sp,
                             fontWeight = FontWeight.SemiBold
                         )
                     },
                     colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                         containerColor = colorResource(id = R.color.appbarcolor),
                         titleContentColor = Color.White
                     ),

                     )
            
        },
        content = {
                padding-> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(
                        id = hangmanImages.getOrNull(incorrectGuessCount)
                            ?: R.drawable.hangman_default
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
                if (!gameInProgress && !outOfAttempts)
                {
                    Text(
                        text = "Kazandınız!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.Green
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        selectedWord.forEach{ char ->
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    letterSpacing = 4.sp
                                )
                            )
                            {
                                if (clickedCharacters.contains(char) || char.isWhitespace())
                                {
                                    append(char.toString())
                                } else {
                                    append("_")
                                }
                            }
                            append(" ")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                if (!gameInProgress)
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    )
                    {
                        Text(
                            text = "Doğru Kelime: $selectedWord",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            color =
                            if (outOfAttempts) Color.Red
                            else Color.Green
                        )
                        Button(onClick =
                        {
                            navController.popBackStack()
                        },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = colorResource(id = R.color.appbarcolor)
                            )) {
                            Text(text = "Ana Sayfaya Dön")
                        }

                    }
                }
                else
                {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange =
                        {
                            inputText = it
                        },
                        label = { Text("Tahmin Klavyesi") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            handleGuess()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.appbarcolor),
                            contentColor = Color.White
                        )
                    )
                    {
                        Text(text = "Tahmin et")
                    }

                }
            }
         }
    )
}


      
        
    
    