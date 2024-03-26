package org.dotsdev.iluva.database.transaction

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.UUID
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.table.UserTable
import org.dotsdev.iluva.database.repository.UserPersistence
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class UserTransactionTest : FunSpec({
    beforeTest {
        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    val transaction = UserPersistence()
    val email = "mail@mail.com"
    val phoneNumber = "12345"

    context("Create User") {
        test("create user should return user id") {
            val id = transaction.create(User(firstName = "John"))

            val user = transaction.find(id)

            user shouldNotBe null
            user?.id shouldBe UUID.fromString(id)
        }

        test("create user with the same unique index should throws exception") {
            transaction.create(User(firstName = "John", email = "mail@mail.com"))

            shouldThrow<Exception> {
                transaction.create(User(firstName = "Doe", email = "mail@mail.com"))
            }
        }
    }

    context("Find User by Id") {
        test("findByID should return user by given id") {
            val userId = transaction.create(User())

            val user = transaction.find(userId)

            user shouldNotBe null
            user?.id shouldBe UUID.fromString(userId)
        }

        test("findByID should return null when user not found") {
            val id = UUID.randomUUID()
            transaction.create(User())

            val user = transaction.find(id.toString())

            user shouldBe null
        }
    }

    context("Find User by Email") {
        test("findByEmail should return user by given email") {
            val user = transaction.findByEmail(email)

            user shouldNotBe null
            user?.email shouldBe email
        }

        test("findByEmail should return null when user not found") {
            val user = transaction.findByEmail("test@mail.com")

            user shouldBe null
        }
    }

    context("Find User by Phone Number") {
        test("findByPhoneNumber should return user by given email") {
            transaction.create(User(phoneNumber = phoneNumber))

            val user = transaction.findByPhoneNumber(phoneNumber)

            user shouldNotBe null
            user?.phoneNumber shouldBe phoneNumber
        }

        test("findByPhoneNumber should return null when user not found") {
            val user = transaction.findByPhoneNumber("1234")

            user shouldBe null
        }
    }

    context("Check if user exists") {
        test("isExists should return true when user exists by given email or phone number") {
            val isExistByEmail = transaction.isExists(email)
            val isExistByPhoneNumber = transaction.isExists(phoneNumber)

            isExistByEmail shouldBe true
            isExistByPhoneNumber shouldBe true
        }

        test("isExists should return false when user not found") {
            val isExistByEmail = transaction.isExists("test@mail.com")
            val isExistByPhoneNumber = transaction.isExists("1234")

            isExistByEmail shouldBe false
            isExistByPhoneNumber shouldBe false
        }
    }
})