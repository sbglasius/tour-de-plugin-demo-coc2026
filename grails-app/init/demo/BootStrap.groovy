package demo

class BootStrap {
    BootStrapService bootStrapService
    
    def init = {
        bootStrapService.createUserAndRoles()
        bootStrapService.createOrderData()
    }

    def destroy = {
    }
    
}