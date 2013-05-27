package comics

import org.springframework.dao.DataIntegrityViolationException

import content.Comic;

class ComicController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [comicInstanceList: Comic.list(params), comicInstanceTotal: Comic.count()]
    }

    def create() {
        [comicInstance: new Comic(params)]
    }

    def save() {
        def comicInstance = new Comic(params)
        if (!comicInstance.save(flush: true)) {
            render(view: "create", model: [comicInstance: comicInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'comic.label', default: 'Comic'), comicInstance.id])
        redirect(action: "show", id: comicInstance.id)
    }

    def show(Long id) {
        def comicInstance = Comic.get(id)
        if (!comicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "list")
            return
        }

        [comicInstance: comicInstance]
    }

    def edit(Long id) {
        def comicInstance = Comic.get(id)
        if (!comicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "list")
            return
        }

        [comicInstance: comicInstance]
    }

    def update(Long id, Long version) {
        def comicInstance = Comic.get(id)
        if (!comicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (comicInstance.version > version) {
                comicInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'comic.label', default: 'Comic')] as Object[],
                          "Another user has updated this Comic while you were editing")
                render(view: "edit", model: [comicInstance: comicInstance])
                return
            }
        }

        comicInstance.properties = params

        if (!comicInstance.save(flush: true)) {
            render(view: "edit", model: [comicInstance: comicInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'comic.label', default: 'Comic'), comicInstance.id])
        redirect(action: "show", id: comicInstance.id)
    }

    def delete(Long id) {
        def comicInstance = Comic.get(id)
        if (!comicInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "list")
            return
        }

        try {
            comicInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'comic.label', default: 'Comic'), id])
            redirect(action: "show", id: id)
        }
    }
}
