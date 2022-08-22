include(
        ":app", ":login_domain", ":appuser", ":home_ui", ":home_data", ":home_domain",
        ":login_data",
        ":login_ui",
        ":core_data",
        ":core_domain",
        ":app_domain",
        ":app_data",
        ":trading_api",
        ":trading_api_mock",
        ":core_ui",
        ":google-pay-client-api-1.0.1",
        ":nsdl-esign-2.1-v-1.3.1",
        ":appusercore",
        ":validator",
        ":annotations",
        ":moslcommons",
        ":appcore",
        ":flashbar",
        ":domaincore",
        ":tokenmanager",
        ":debugview",
        ":debugviewcore",
        ":rx_preferences"
)

rootProject.name = "FilmBazar"

project(":app").projectDir = File("investor/app")
project(":app_data").projectDir = File("investor/app_data")
project(":app_domain").projectDir = File("investor/app_domain")

project(":trading_api_mock").projectDir = File("service/trading_api_mock")
project(":trading_api").projectDir = File("service/trading_api")

project(":login_ui").projectDir = File("login/login_ui")
project(":login_data").projectDir = File("login/login_data")
project(":login_domain").projectDir = File("login/login_domain")

project(":home_ui").projectDir = File("home/home_ui")
project(":home_data").projectDir = File("home/home_data")
project(":home_domain").projectDir = File("home/home_domain")

project(":appusercore").projectDir = File("helper/appusercore")
project(":validator").projectDir = File("helper/validator")
project(":annotations").projectDir = File("helper/annotations")
project(":moslcommons").projectDir = File("helper/moslcommons")
project(":appcore").projectDir = File("helper/appcore")
project(":flashbar").projectDir = File("helper/flashbar")
project(":domaincore").projectDir = File("helper/domaincore")
project(":tokenmanager").projectDir = File("helper/tokenmanager")
project(":debugview").projectDir = File("helper/debugview")
project(":debugviewcore").projectDir = File("helper/debugviewcore")
