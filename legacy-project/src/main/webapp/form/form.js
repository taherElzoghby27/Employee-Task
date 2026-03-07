angular.module("myEmployeeApp").service("employeeService", function ($http) {
    var $employeeService = this;
    $employeeService.getEmployees = function ($employeeForm) {
        return $http.get('http://localhost:8080/legacy-project/employee', { params: $employeeForm })
            .then(function (response) {
                return response.data;
            }).catch(function (error) {
                console.error('Error fetching employees:', error);
                return [];
            });
    };
    $employeeService.getContractTypes = function () {
        return $http.get('http://localhost:8080/legacy-project/contract-type')
            .then(function (response) {
                return response.data;
            }).catch(function (error) {
                console.error('Error fetching contract types:', error);
                return [];
            });
    };
    $employeeService.getDepartments = function () {
        return $http.get('http://localhost:8080/legacy-project/department')
            .then(function (response) {
                return response.data;
            }).catch(function (error) {
                console.error('Error fetching departments:', error);
                return [];
            });
    };
});

angular.module("myEmployeeApp").component("formEmployee", {
    templateUrl: "form/form.html",
    controllerAs: "$formCtrl",
    controller: ["employeeService", function (employeeService) {
        var $formCtrl = this;
        $formCtrl.cities = ['London', 'Alexandria', 'Giza', 'Luxor', 'Aswan', 'Port Said', 'Suez', 'Ismailia', 'Cairo', 'Tanta'];
        $formCtrl.statuses = ['0', '1'];
        $formCtrl.departments = [];
        $formCtrl.contractTypes = [];
        $formCtrl.employeeForm = {
            employeeCode: '',
            employeeName: '',
            birthDate: '',
            birthCity: '',
            employeeId: '',
            department: '',
            jobTitle: '',
            directManager: '',
            contractType: '',
            status: ''
        };
        $formCtrl.onSubmit = function (event) {
            if (event) {
                event.preventDefault();
            }
            console.log('Employee Data:', $formCtrl.employeeForm);
            $formCtrl.loadEmployees();
        };
        $formCtrl.getStatusClass = function (status) {
            return status === "1" ? 'text-success' : 'text-danger';
        };

        $formCtrl.loadEmployees = function () {
            employeeService.getEmployees($formCtrl.employeeForm).then(function (data) {
                $formCtrl.employees = data;
                console.log('Employees:', $formCtrl.employees);
            });
        };
        $formCtrl.loadDepartments = function () {
            employeeService.getDepartments().then(function (data) {
                $formCtrl.departments = data;
            });
        };
        $formCtrl.loadContractTypes = function () {
            employeeService.getContractTypes().then(function (data) {
                $formCtrl.contractTypes = data;
            });
        };

        $formCtrl.$onInit = function () {
            $formCtrl.loadEmployees();
            $formCtrl.loadDepartments();
            $formCtrl.loadContractTypes();
        };
    }]
});