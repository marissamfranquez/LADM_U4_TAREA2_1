package mx.edu.ittepic.incomingcalls

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var requestCallLog = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG), requestCallLog)
        }
        button.setOnClickListener {
            getSentCalls()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getSentCalls() {
        var cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        if(cursor!=null){
            textView.setText("LLAMADAS ENTRANTES:\n")
            if(cursor.moveToFirst()){
                do{
                    var columTypeCall = cursor.getColumnIndex(CallLog.Calls.TYPE)
                    var typeSent = CallLog.Calls.INCOMING_TYPE
                    if (cursor.getInt(columTypeCall) == typeSent){
                        var columnNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER)
                        var columnDate = cursor.getColumnIndex(CallLog.Calls.DATE)
                        var number = cursor.getString(columnNumber)
                        var date = cursor.getString(columnDate)
                        var dateFormat = Date(date.toLong())
                        textView.setText("${textView.text}\nNÚMERO:$number\nFECHA:$dateFormat\n******************\n")
                    }
                }while(cursor.moveToNext())
            }
            cursor.close()
        }
    }
}