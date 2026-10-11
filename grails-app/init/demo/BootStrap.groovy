package demo

class BootStrap {
    BootStrapService bootStrapService
    
    def init = {
        bootStrapService.createOrderData()
    }

    def destroy = {
    }
    
}