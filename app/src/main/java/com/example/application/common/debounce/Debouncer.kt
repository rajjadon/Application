package com.example.application.common.debounce

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*

/**
 * This class provide some debounce method, which means that the function will attend only last
 * call within a given time period
 *
 * This methods or class will solve the issue by eliminating all the unnecessary calls, which
 * make by the user within a certain time period.
 *
 */
object Debouncer {

    /**
     *  This is a [Generic Debouncer][Debouncer] method
     *
     *  ### Example of use
     *  ```
     *  class MainActivity {
     *      private val scope = MainScope()
     *
     *      override fun onCreate() {
     *          val debouncedFoo = debouncer(scope ,300L, {
     *              todo write your debounced code here
     *          }
     *
     *          debouncedFoo(x)
     *      }
     *  }
     *  ```
     *
     *   ### Example of use: #Java
     *
     *  ```
     *  public class MainActivity() extends AppCompatActivity(){
     *  private EditText editText;
     *      protected void onCreate(Bundle saveInstanceState) {
     *          editText = findViewById(R.id.editText);
     *
     *      Function1<String, Unit> function = Debouncer.debounce(1000, getLifecycle(), (s -> {
     *          Log.d("TAG", "Value: " + s);
     *          return Unit.INSTANCE;
     *      }));
     *
     *          editText.addTextChangeListener(new TextWatcher() {
     *               @Override
     *               public void afterTextChanged(Editable s) {
     *                  function.invoke(s.toString());
     *               }
     *          });
     *
     *      }
     *
     *  }
     *```
     *  @param lifecycle is a [LifeCycle][Lifecycle]
     *  @param timeInMillis it takes milli seconds as a [Long] default 300ms
     *  @param function it take function (T)-> [Unit] as a parameter
     *  @return (T)-> [Unit] after eliminating all unnecessary calls
     *
     **/
    @JvmStatic
    fun <T> debounce( //(param) -> ReturnType
        timeInMillis: Long = 300L,
        lifecycle: Lifecycle,
        @NonNull function: (T) -> Unit
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = lifecycle.coroutineScope.launch {
                delay(timeInMillis)
                function(param)
            }
        }
    }

    //todo not completed yet!, modification required
    /**
     *  This is a [Generic Function [(Generic]} Extension Debouncer][Debouncer] method
     * ### Example of use:
     *  ```
     *  class MainActivity {
     *      private val scope = MainScope()
     *      override fun onCreate() {
     *          val foo = { param ->
     *              todo your lambda function, write your code here!
     *          }.debounce(scope, 300L)
     *
     *          foo(x)
     *      }
     *  }
     *  ```
     *  @param scope is a [Coroutine Scope][CoroutineScope]
     *  @param timeInMillis it takes milli seconds as a [Long] default 300ms*
     *  @return (T)-> [Unit] after eliminating all unnecessary calls
     **/
    @JvmStatic
    fun <T> ((T) -> Unit).debounce(
        timeInMillis: Long,
        lifecycle: Lifecycle
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = lifecycle.coroutineScope.launch {
                delay(timeInMillis)
                this@debounce(param)
            }

        }
    }
}