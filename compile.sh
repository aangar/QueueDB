FLAG="$1"
BASE_DIR="$PWD"

compile_java_files() {
    DIR="$1"
    cd "$BASE_DIR/$DIR/"
        for file in *
    do
        str="$file"
        len=${#str}
        end=$(($len-4));
        ext=$( echo "$file" |cut -c$end-$len )
        if [[ "$ext" == ".java" ]]
        then
            javac "./$file" 
        fi
    done
    clear
    echo "Java Files compiled in $DIR."
    cd "$BASE_DIR"
}

delete_java_classes() {
     DIR="$1"
    cd "$BASE_DIR/$DIR/"
        for file in *
    do
        str="$file"
        len=${#str}
        end=$(($len-5));
        ext=$( echo "$file" |cut -c$end-$len )
        if [[ "$ext" == ".class" ]]
        then
            rm "./$file" 
            echo "Removed Class File $file"
        fi
    done
    cd "$BASE_DIR"   
}

if [[ "$FLAG" == "tree" ]]
then
    compile_java_files "./binarytrees"
elif [[ "$FLAG" == "dynamic" ]]
then
    compile_java_files queuedb/DAO
    compile_java_files queuedb/Objects
    compile_java_files queuedb
fi

cd "$BASE_DIR"
javac Source.java
clear
java Source "$FLAG" "$PWD"

delete_java_classes queuedb/DAO
delete_java_classes queuedb/Objects
delete_java_classes queuedb