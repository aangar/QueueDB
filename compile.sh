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
        ext=$( echo "$file" |cut -c"$end"-"$len" )
        if [[ "$ext" == ".class" ]]
        then
            rm "./$file" 
        fi
    done
    cd "$BASE_DIR"   
}


compile_java_files queuedb/DAO
compile_java_files queuedb/Objects
compile_java_files queuedb
compile_java_files server


cd "$BASE_DIR"
javac Source.java
java Source "$PWD"

delete_java_classes queuedb/DAO
delete_java_classes queuedb/DAO/tests
delete_java_classes queuedb/Objects
delete_java_classes queuedb
delete_java_classes server