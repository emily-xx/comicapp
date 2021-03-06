package comicapp.people

import comicapp.content.Comic

class Comment {

	static belongsTo = [person: Person, comic: Comic]

	Date dateCreated
	Date lastUpdated

	String body

	static constraints = {
		person(nullable: false)
		comic(nullable: false)

		dateCreated(nullable: false)
		lastUpdated(nullable: false)

		body(nullable: false, maxLength: 300)
	}

	static mapping = {
		body type: 'text'
	}
}
