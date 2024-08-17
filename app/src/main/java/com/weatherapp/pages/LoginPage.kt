import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.MainActivity
import com.weatherapp.RegisterActivity


@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome!",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        DataField(value = email, onValueChange = { email = it } , label = "E-mail")
        PasswordField(value = password, onValueChange = {password = it}, label = "Password")
        Row(modifier = modifier) {
            Button(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) {
                        task ->
                        if (task.isSuccessful) {
                            activity.startActivity( Intent(activity, MainActivity::class.java).setFlags(FLAG_ACTIVITY_SINGLE_TOP));
                            Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            password = ""; email = "" ;
                        }else{
                            Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Sign in")
            }
            Spacer(modifier = Modifier.size(24.dp))
            Button(
                onClick = { email = ""; password = "" },
            ) {
                Text("Clear")
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
        ClickableText(
            text = "Don't have an account? Sign Up",
            onClick = {
                activity?.startActivity(
                    Intent(activity, RegisterActivity::class.java).setFlags(
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            }
        )
    }
}