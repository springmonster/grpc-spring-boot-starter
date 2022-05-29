package net.devh.boot.grpc.examples.local.server.complex;

import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.examples.lib.ComplexGrpc;
import net.devh.boot.grpc.examples.lib.ComplexReply;
import net.devh.boot.grpc.examples.lib.ComplexRequest;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;

@GrpcService
public class GrpcComplexServerService extends ComplexGrpc.ComplexImplBase {

    @Override
    public void sayComplex(ComplexRequest request, StreamObserver<ComplexReply> responseObserver) {
        String name = request.getName();
        int age = request.getAge();
        Map<String, String> addressMap = request.getAddressMap();
        ProtocolStringList hobbyList = request.getHobbyList();

        ComplexReply complexReply = ComplexReply.newBuilder()
                .setName(name)
                .setAge(age)
                .addAllHobby(hobbyList)
                .putAllAddress(addressMap)
                .setDescription(name + age + hobbyList + addressMap)
                .build();

        responseObserver.onNext(complexReply);
        responseObserver.onCompleted();
    }
}
