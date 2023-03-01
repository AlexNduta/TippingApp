package com.example.tippingapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Input
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Inputfield(
    modifier: Modifier=Modifier,
    valueState: MutableState<String>,
    labelID: String,
    isSIngleLine:Boolean,
    enabled:Boolean,
    keyboardType:KeyboardType= KeyboardType.Number,
    imeAction:ImeAction= ImeAction.Next,
    onAction: KeyboardActions=KeyboardActions.Default
){
    OutlinedTextField(value = valueState.value,
        //what hapens if our value changes 
        onValueChange ={valueState.value=it} ,
        //this is the description displayed
    label = { Text(text = labelID)},
        //The icon to display
        leadingIcon = { Icon(imageVector = Icons.Rounded.Input, contentDescription = "Money Icon")},
        //Attach to the top level varriable passed
        singleLine = isSIngleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier=modifier.padding(bottom = 10.dp, start = 10.dp ).fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction)
        
        )
    
}