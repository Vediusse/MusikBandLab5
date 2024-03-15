javac -d . *.java */*.java

jar cfm Lab3.jar META-INF/MANIFEST.mf -C . ./

export XML_FILE_PATH=~/.config/lab5/default.xml

export XML_FILE_PATH_WITH_ERROR=~/.config/lab5/error.xml


export SCRIPT_FILE=~/.config/lab5/script.sh

java -jar Lab3.jar

rm -rf Lab3
