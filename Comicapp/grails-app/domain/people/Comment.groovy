package people

import comics.*
import content.Comic;

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
}
