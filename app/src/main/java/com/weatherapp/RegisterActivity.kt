package com.weatherapp

import ClickableText
import DataField
import PasswordField
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.ui.theme.WeatherAppTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterPage()
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterPage(modifier: Modifier = Modifier){
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column (
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Create Account.",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        DataField(value = name, onValueChange = {name = it}, label = "Full name")
        DataField(value = email, onValueChange = {email = it}, label = "Email")
        PasswordField(value = password, onValueChange = {password = it}, label = "Password")
        PasswordField(value = confirmPassword, onValueChange = {confirmPassword = it}, label = "Confirm Password")
        Row(modifier = modifier) {
            Button(
                onClick = {
                    Toast.makeText(activity, "Registration OK!", Toast.LENGTH_LONG).show()
                    activity?.startActivity(
                        Intent(activity, LoginActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                    name = "" ; email = ""; password = ""; confirmPassword = "";
                },
                enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
            ) {
                Text("Sign up")
            }
            Spacer(modifier = Modifier.size(24.dp))
            Button(
                onClick = { name = "" ; email = ""; password = ""; confirmPassword = "" },
            ) {
                Text("Clear")
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
        ClickableText(
            text = "Already have account? Sign In",
            onClick = {
                activity?.startActivity(
                    Intent(activity, LoginActivity::class.java).setFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            }
        )
    }
}
