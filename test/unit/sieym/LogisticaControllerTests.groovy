package sieym



import org.junit.*
import grails.test.mixin.*

@TestFor(LogisticaController)
@Mock(Logistica)
class LogisticaControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/logistica/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.logisticaInstanceList.size() == 0
        assert model.logisticaInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.logisticaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.logisticaInstance != null
        assert view == '/logistica/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/logistica/show/1'
        assert controller.flash.message != null
        assert Logistica.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/logistica/list'


        populateValidParams(params)
        def logistica = new Logistica(params)

        assert logistica.save() != null

        params.id = logistica.id

        def model = controller.show()

        assert model.logisticaInstance == logistica
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/logistica/list'


        populateValidParams(params)
        def logistica = new Logistica(params)

        assert logistica.save() != null

        params.id = logistica.id

        def model = controller.edit()

        assert model.logisticaInstance == logistica
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/logistica/list'

        response.reset()


        populateValidParams(params)
        def logistica = new Logistica(params)

        assert logistica.save() != null

        // test invalid parameters in update
        params.id = logistica.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/logistica/edit"
        assert model.logisticaInstance != null

        logistica.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/logistica/show/$logistica.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        logistica.clearErrors()

        populateValidParams(params)
        params.id = logistica.id
        params.version = -1
        controller.update()

        assert view == "/logistica/edit"
        assert model.logisticaInstance != null
        assert model.logisticaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/logistica/list'

        response.reset()

        populateValidParams(params)
        def logistica = new Logistica(params)

        assert logistica.save() != null
        assert Logistica.count() == 1

        params.id = logistica.id

        controller.delete()

        assert Logistica.count() == 0
        assert Logistica.get(logistica.id) == null
        assert response.redirectedUrl == '/logistica/list'
    }
}
