# 本地接口实现

## 编译过程

本程序使用CMake进行编译，请确保已经正确安装CMake。

```
mkdir build
cd build
cmake -G "Visual Studio 14 2015 Win64" ..
cmake --build .
```

如果想打RELEASE，使用如下命令：

```
cmake --build . -- /p:Configuration=Release
```

