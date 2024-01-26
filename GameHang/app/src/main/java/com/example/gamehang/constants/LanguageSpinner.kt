package com.example.gamehang.constants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamehang.R

@Composable
fun LanguageSpinner(
    choice:List<String>,
    selectedLanguage:String,
    onSelected:(String) -> Unit
)
{
    var selectedItem by remember{ mutableStateOf(selectedLanguage) }
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Box(
            modifier = Modifier
                .width(120.dp)
                .border(
                    width = 1.dp,
                    color = Color.White
                )
                .clickable {
                    isExpanded = !isExpanded
                }
        )
        {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedItem,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
                )
            Icon(
                painter = painterResource(id = R.drawable.arrowdown),
                contentDescription = "",
                tint = Color.White)
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(color = Color.White)
            )
            {
                choice?.forEach{
                    choose ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = choose,
                                color= Color.Black
                            ) },
                        onClick = {
                        isExpanded = false
                        selectedItem = choose
                        onSelected(choose)
                    }
                    )
                }


            }

        }
        }
    }
}

