#!/bin/bash
cwd="$pwd"
echo -e "{"
echo -e "\t\"name\": \"FOP-2223-H$1\","
echo -e "\t\"assignmentIds\": [\"h$1\"],"
echo -e "\t\"sourceSets\": ["
echo -e "\t\t{"
echo -e "\t\t\t\"name\": \"grader\","
echo -e "\t\t\t\"files\": ["
cd src/grader/java || exit
first=true
for f in $(find . -iname "*.java" | sed 's|^./||');do
    if [ $first = false ]; then
        printf ",\n"
    fi
    printf "\t\t\t\t\"%s\"" "$f"
    first=false
done
printf "\n"
echo -e "\t\t\t]"
echo -e "\t\t},"
echo -e "\t\t{"
echo -e "\t\t\t\"name\": \"solution\","
echo -e "\t\t\t \"files\": ["
cd ../../main/java || exit
first=true
for f in $(find . -iname "*.java" | sed 's|^./||');do
    if [ $first = false ]; then
        printf ",\n"
    fi
    printf "\t\t\t\t\"%s\"" "$f"
    first=false
done
cd ../../test/java || exit
for f in $(find . -iname "*.java" | sed 's|^./||');do
    if [ $first = false ]; then
        printf ",\n"
    fi
    printf "\t\t\t\t\"%s\"" "$f"
    first=false
done
printf "\n"
echo -e "\t\t\t]"
echo -e "\t\t}"
echo -e "\t]"
echo -e "}"
cd "../../.."
