#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
#
#   Gradle start up script for POSIX environments
#
##############################################################################

# Set script variables for configuration.
APP_BASE_NAME=$(basename "$0")
APP_HOME=$(cd "$(dirname "$0")" && pwd)

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "$(uname)" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        if [ -x "$JAVA_HOME/bin/java" ] ; then
            JAVACMD="$JAVA_HOME/bin/java"
        else
            die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
        fi
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" ] && [ "$darwin" = "false" ] && [ "$nonstop" = "false" ] ; then
    if [ -r "/etc/rc.d/init.d/functions" ] ; then
        # Source the functions file
        . "/etc/rc.d/init.d/functions"
        # Increase the maximum file descriptors (which defaults to 1024)
        if [ -a /proc/sys/fs/file-max ] ; then
            MAX_FD=$(cat /proc/sys/fs/file-max)
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if [ "$darwin" = "true" ] ; then
    GRADLE_OPTS="$GRADLE_OPTS "-Xdock:name=$APP_BASE_NAME" "-Xdock:icon=$APP_HOME/media/gradle.icns""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$cygwin" = "true" ] || [ "$msys" = "true" ] ; then
    APP_HOME=$(cygpath --path --mixed "$APP_HOME")
    CLASSPATH=$(cygpath --path --mixed "$CLASSPATH")

    JAVACMD=$(cygpath --unix "$JAVACMD")

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSRAW=$(find . -type d -maxdepth 1 -name "[A-Za-z]:" | sort)
    SEP=$(echo -n "$PATH" | head -c 1)
    ROOTDIRS=$(echo "$ROOTDIRSRAW" | sed -e "s;^;$SEP;;" -e "s;$SEP$;;" | tr "$SEP" "|")
    if [ -n "$ROOTDIRS" ] ; then
        PATTERN="(^($ROOTDIRS))"
        if [ "$cygwin" = "true" ] ; then
            CLASSPATH=$(echo "$CLASSPATH" | sed -e "s;$PATTERN;/cygdrive/\1;g" -e "s;\\\\;/;g")
        fi
        if [ "$msys" = "true" ] ; then
            CLASSPATH=$(echo "$CLASSPATH" | sed -e "s;$PATTERN;/\\1;g" -e "s;\\\\;/;g")
        fi
    fi
fi

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\''/g;1s/^/'/;s//' "/; done
}
saved_args=$(save "$@")

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$saved_args"

# by default we should be in the correct project directory, but when run from Finder on Mac, the cwd is wrong
if [ "$(uname)" = "Darwin" ] && [ -z "$HOME" -o "$HOME" = "/" ]; then
  cd "$(dirname "$0")"
fi

exec "$JAVACMD" "$@"
