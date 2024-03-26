package org.dotsdev.iluva.unit

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import org.dotsdev.iluva.AuthRepository
import org.dotsdev.iluva.User
import org.dotsdev.iluva.database.repository.UserPersistence
import org.dotsdev.iluva.dto.request.LoginRequest

class AuthRepositoryTest : FunSpec({

    lateinit var transaction: UserPersistence
    lateinit var target: AuthRepository

    beforeTest {
        transaction = mockk()
        target = AuthRepository(transaction)
    }

    test("find user by email should return user") {
        val request = LoginRequest(email = "test@example.com", password = "password")
        val user = User(id = UUID.randomUUID(), firstName = "John", lastName = "Doe", email = "test@example.com", password = "password")

        coEvery { target.findUserByEmail(request) } returns user

        val result = target.findUserByEmail(request)

        result.fold({
            // Do nothing
        }) {
            it shouldBe user
        }

        coVerify(exactly = 1) { target.findUserByEmail(request) }
    }

    isolationMode = IsolationMode.InstancePerTest

    afterTest {
        clearMocks(target)
    }
})