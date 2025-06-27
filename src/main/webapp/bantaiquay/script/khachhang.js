window.khachhangCtrl = function ($scope, $http) {
    const url = "http://localhost:8080/khachhang";

    $scope.listKhachHang = [];

    $scope.loadPage = function () {
        $http.get(url + "/table")
            .then(function (response) {
                $scope.listKhachHang = response.data;

                if ($scope.listKhachHang.length === 0) {
                    $scope.emptyMessage = "Danh sách trống!";
                } else {
                    $scope.emptyMessage = "";
                }
            })
            .catch(function (error) {
                console.error('Lỗi:', error);
            });
    };

    $scope.resetForm = function () {
        $scope.ten = "";
        $scope.email = "";
        $scope.passw = "";
        $scope.gioiTinh = "";
        $scope.sdt = "";
        $scope.diaChi = "";
        $scope.trangThai = "";
    };

    $scope.loadPage();
}
