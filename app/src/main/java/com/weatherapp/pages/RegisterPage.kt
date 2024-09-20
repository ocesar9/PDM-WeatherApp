import android.app.Activity
import android.content.Intent
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
import com.weatherapp.LoginActivity
import com.weatherapp.db.FBDatabase
import androidx.compose.runtime.remember
import com.weatherapp.models.User


@Composable
fun RegisterPage(modifier: Modifier = Modifier){
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    val fbDB = remember { FBDatabase() }

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
                    Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) {
                        task  ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity,"Registro OK!", Toast.LENGTH_LONG).show();
                            activity.finish()
                            fbDB.register(User(name, email))
                            email = "" ; password = ""; confirmPassword = ""; name = "";
                        }else {
                            Toast.makeText(activity,"Registro FALHOU!", Toast.LENGTH_LONG).show();
                        }
                    }
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