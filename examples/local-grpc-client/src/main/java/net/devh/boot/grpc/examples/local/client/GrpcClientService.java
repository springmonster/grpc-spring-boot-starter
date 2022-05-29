/*
 * Copyright (c) 2016-2021 Michael Zhang <yidongnan@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.devh.boot.grpc.examples.local.client;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.examples.lib.ComplexGrpc;
import net.devh.boot.grpc.examples.lib.ComplexReply;
import net.devh.boot.grpc.examples.lib.ComplexRequest;
import net.devh.boot.grpc.examples.lib.HelloReply;
import net.devh.boot.grpc.examples.lib.HelloRequest;
import net.devh.boot.grpc.examples.lib.SimpleGrpc.SimpleBlockingStub;
import net.devh.boot.grpc.examples.local.client.entity.UserInput;
import net.devh.boot.grpc.examples.local.client.entity.UserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Michael (yidongnan@gmail.com)
 * @since 2016/11/8
 */
@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private SimpleBlockingStub simpleStub;

    @GrpcClient("local-grpc-server")
    private ComplexGrpc.ComplexBlockingStub complexBlockingStub;

    public String sendMessage(final String name) {
        try {
            final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

    public UserOutput sendComplexMessage(UserInput userInput) {
        try {
            ComplexRequest complexRequest = ComplexRequest.newBuilder().setName(userInput.getName()).setAge(userInput.getAge()).putAllAddress(userInput.getAddress()).addAllHobby(userInput.getHobby()).build();

            final ComplexReply response = this.complexBlockingStub.sayComplex(complexRequest);
            return UserOutput.builder()
                    .name(response.getName())
                    .age(response.getAge())
                    .address(response.getAddressMap())
                    .hobby(response.getHobbyList())
                    .description(response.getDescription())
                    .build();
        } catch (final StatusRuntimeException e) {
            return null;
        }
    }
}
