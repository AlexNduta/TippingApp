package com.example.tippingapp

import android.os.Bundle
import android.util.Log
import android.util.Range
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tippingapp.components.Inputfield
import com.example.tippingapp.ui.theme.TippingAppTheme
import com.example.tippingapp.utils.calculateTotaltip
import com.example.tippingapp.utils.totalPerperson
import com.example.tippingapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp {
               TopHeader()
               MainContent()
           }
        }
    }
}
//@Preview
@Composable
fun TopHeader(totalPerPerson:Double=0.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(20.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(18.dp))) //Aternative method to set the rounded conners
      //  .clip(shape= RoundedCornerShape(corner = CornerSize(12.dp)))
    , color = Color(0xFFACD3CF)
    ){
Column(
    modifier=Modifier.padding(12.dp),
    verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally) {
    //formart my output to have extra zeros
    val total ="%.2f".format(totalPerPerson)
Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
Text(text = "Ksh $total",
style = MaterialTheme.typography.h4,
fontWeight = FontWeight.ExtraBold)

}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){
    BillForm(){billAmt->
        Log.d("AMT", "MainContent: ${billAmt.toInt() * 100}")
    }

}


@Composable
fun MyApp(content: @Composable ()->Unit){
    TippingAppTheme {
    Surface(
        color = MaterialTheme.colors.background) {
        content()
    }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier=Modifier,
             onValueChange: (String)->Unit ={}
){

    val sliderPosition = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPosition.value* 100).toInt()
    var SplitBystate= remember {
        mutableStateOf(1)
    }
    var Range= IntRange(start = 1, endInclusive = 50)
    val totalBillState= remember {
        mutableStateOf("")
    }
    //We use this variable to check if the state variable is empty
    //pass the totalBillState and invoke the trim method
    val validState= remember (totalBillState.value){
        //The trim() method gets rid of any excess whitespace
        //the isNotEmpty() method confirms if our state variable has a value or not
        totalBillState.value.trim().isNotEmpty()
    }
    //the variable refrences the API that controls our keyboard onshow and hide
    val keyboardControler= LocalSoftwareKeyboardController.current

    val tipAmount= remember {
        mutableStateOf(0.0)
    }

    val finaltotalperperson= remember {
        mutableStateOf(0.0)
    }
    Column {
        TopHeader(totalPerPerson = finaltotalperperson.value)


    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
            Inputfield(
                valueState =totalBillState
                , labelID = "Enter bill"
                , isSIngleLine = true
                , enabled = true,
                onAction = KeyboardActions {
                    //if our valid state is false, return what the keyboard allows us to do
                    if (!validState) return@KeyboardActions
                    //get the actual value
                    onValueChange(totalBillState.value.trim())
                    keyboardControler?.hide()
                })
            if (validState){
                Row(modifier=Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.Start,) {
                    Text(text = "Split")
                    Spacer(modifier=Modifier.width(120.dp))

                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End,) {
                        RoundIconButton(imageVector =Icons.Default.Remove
                            , onClick = {
                            SplitBystate.value=
                                if (SplitBystate.value> 1) SplitBystate.value -1
                                else 1



                                finaltotalperperson.value=
                                    totalPerperson(total = totalBillState.value.toDouble(),
                                        Split = SplitBystate.value,
                                        percentage = tipPercentage)
                            })
                        Text(text = "${SplitBystate.value}", modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp))
                    }
                    RoundIconButton(imageVector = Icons.Default.Add,
                        onClick = {

                            if (SplitBystate.value< Range.last){
                                SplitBystate.value= SplitBystate.value+1
                            }

                            finaltotalperperson.value=
                                totalPerperson(total = totalBillState.value.toDouble(),
                                    Split = SplitBystate.value,
                                    percentage = tipPercentage)
                        })

                }
                //Tip Row
                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    Text(text = "Tip", modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(text = "$ ${tipAmount.value}")
                }
                Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$tipPercentage%")

                    Spacer(modifier = Modifier.height(15.dp))

                    Slider(value = sliderPosition.value,
                        onValueChange ={newValue->
                            sliderPosition.value= newValue
                            tipAmount.value=
                                calculateTotaltip(totalBill =  totalBillState.value.toDouble(), tipPrcent =  tipPercentage)

                            finaltotalperperson.value=
                                totalPerperson(total = totalBillState.value.toDouble(),
                                Split = SplitBystate.value,
                                percentage = tipPercentage)
                        } ,
                        modifier=Modifier.padding(start = 16.dp, end = 16.dp) ,
                    steps = 5)
                }



                }else{
                Box() {

                }
            }


                }

            }


        }

    }




//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TippingAppTheme {
//MyApp {
//    Text(text = "Mic Testing..1,2")
//}
//    }
//}