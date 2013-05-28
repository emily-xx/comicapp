package comicapp.content



import org.junit.*

import comicapp.content.ComicController

import comicapp.content.Comic
import grails.test.mixin.*

@TestFor(ComicController)
@Mock(Comic)
class ComicControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/comic/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.comicInstanceList.size() == 0
        assert model.comicInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.comicInstance != null
    }

    void testSave() {
        controller.save()

        assert model.comicInstance != null
        assert view == '/comic/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/comic/show/1'
        assert controller.flash.message != null
        assert Comic.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/comic/list'

        populateValidParams(params)
        def comic = new Comic(params)

        assert comic.save() != null

        params.id = comic.id

        def model = controller.show()

        assert model.comicInstance == comic
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/comic/list'

        populateValidParams(params)
        def comic = new Comic(params)

        assert comic.save() != null

        params.id = comic.id

        def model = controller.edit()

        assert model.comicInstance == comic
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/comic/list'

        response.reset()

        populateValidParams(params)
        def comic = new Comic(params)

        assert comic.save() != null

        // test invalid parameters in update
        params.id = comic.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/comic/edit"
        assert model.comicInstance != null

        comic.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/comic/show/$comic.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        comic.clearErrors()

        populateValidParams(params)
        params.id = comic.id
        params.version = -1
        controller.update()

        assert view == "/comic/edit"
        assert model.comicInstance != null
        assert model.comicInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/comic/list'

        response.reset()

        populateValidParams(params)
        def comic = new Comic(params)

        assert comic.save() != null
        assert Comic.count() == 1

        params.id = comic.id

        controller.delete()

        assert Comic.count() == 0
        assert Comic.get(comic.id) == null
        assert response.redirectedUrl == '/comic/list'
    }
}
