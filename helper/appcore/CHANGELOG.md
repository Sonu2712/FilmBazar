Summary
=======
This library provides a BaseView and BasePresenter class.
It also provide DataAction class

It uses Timber for logging.
It depends on androidx.annotation, io.reactivex.rxjava2, rxandroid package.
It also defines validator and moslcommons package as dependency



Release 1.1.0-alpha**
===============

July 11, 2019 - 1.1.0-alpha4
===========
- Changed all dependency to compileOnly except moslcommons
- Moved Logger to moslcommons

July 05, 2019 - 1.1.0-alpha2
===========
- Changed rxjava & rxandroid to compileOnly
- Removed DateRange, DateUtils, DataFormatUtils & RxJavaHelpers (they are part of moslcommons)
