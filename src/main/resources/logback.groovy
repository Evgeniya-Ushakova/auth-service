import ch.qos.logback.classic.encoder.PatternLayoutEncoder

def appenders = ["CONSOLE"]
def PROJECT_DIR = '.'

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d %level %thread %mdc %logger - %m%n'
    }
}

//appender("COMMON", RollingFileAppender) {
//    file = "${PROJECT_DIR}/logs/common.json"
//    append = true
//
//    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
//        fileNamePattern = "${PROJECT_DIR}/logs/archive/common.%d{yyyy-MM-dd-HH}.%i.zip"
//        maxFileSize = "1GB"
//    }
//    encoder(LogstashEncoder) {
//        includeMdcKeyNames = ['traceId', 'spanId', 'requestId']
//        includeContext = false
//        includeCallerData = true
//    }
//
//}

root(INFO, appenders)