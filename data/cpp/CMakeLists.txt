cmake_minimum_required(VERSION 3.4.1)
if (${CMAKE_BUILD_TYPE} STREQUAL Debug)
    add_definitions(-DDEBUG)
endif ()

add_library(
        native-lib
        SHARED
        /Users/rwnhrmwn/Documents/Irwan/Learning/KMP/pokedex-kmp/data/cpp/native-lib.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        native-lib
        ${log-lib}
)