compile_java_files() {
    DIR="$1"
    cd "$DIR"
        for file in *
    do
        str="$file"
        len=${#str}
        end=$(($len-4));
        ext=$( echo "$file" |cut -c$end-$len )
        if [[ "$ext" == ".java" ]]
        then
            javac ./"$file"
        fi
    done
    clear
    echo "Java Files compiled in $DIR."
}

FLAG="$1"
BASE_DIR="$PWD"
if [[ "$FLAG" == "tree" ]]
then
    compile_java_files "./binarytrees"
elif [[ "$FLAG" == "dynamic" ]]
then
    compile_java_files "./queue"
fi

cd "$BASE_DIR"
javac Source.java
clear
java Source "$FLAG" "$PWD"